package ru.jsft.voteforlunch.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.jsft.voteforlunch.AbstractSpringBootTest;
import ru.jsft.voteforlunch.error.IllegalRequestDataException;
import ru.jsft.voteforlunch.service.UserService;
import ru.jsft.voteforlunch.utils.StringToListMatcher;
import ru.jsft.voteforlunch.web.controller.UserController;
import ru.jsft.voteforlunch.web.controller.dto.UserDto;
import ru.jsft.voteforlunch.web.controller.mapper.UserMapper;
import ru.jsft.voteforlunch.web.json.JsonUtil;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.jsft.voteforlunch.testdata.UserTestData.*;
import static ru.jsft.voteforlunch.utils.MockAuthorization.userHttpBasic;

class UserControllerTest extends AbstractSpringBootTest {

    private static final String REST_URL = UserController.REST_URL + '/';

    private static final StringToListMatcher USER_CONSTRAINTS_MATCHER = new StringToListMatcher("\n", List.of(
                    "[email] Please enter valid e-mail",
                    "[firstName] First name must not be empty",
                    "[lastName] Last name must not be empty",
                    "[roles] must not be empty",
                    "[password] Password must not be empty"));

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper mapper;

    @Test
    void shouldGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)
                        .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_DTO_MATCHER.contentJson(USER_DTO_LIST));
    }

    @Test
    void shouldGet() throws Exception {
        mockMvc.perform(get(REST_URL + "/1")
                        .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_DTO_MATCHER.contentJson(ADMIN_DTO));
    }

    @Test
    void shouldGetAnauthorized() throws Exception {
        mockMvc.perform(get(REST_URL + "/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldCreate() throws Exception {
        UserDto newUser = getNewDto();

        ResultActions resultActions = mockMvc.perform(post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.objectToJson(newUser))
                        .with(userHttpBasic(ADMIN)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        UserDto actual = USER_DTO_MATCHER.readFromJson(resultActions);
        assertThat(actual).isNotNull();
        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(newUser);
        assertThat(actual).usingRecursiveComparison().ignoringFields("password").isEqualTo(userService.findById(actual.getId()));
    }

    @Test
    void shouldDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + "/1").with(userHttpBasic(ADMIN))).andExpect(status().isNoContent());

        assertThrows(IllegalRequestDataException.class, () -> userService.findById(1L));
    }

    @Test
    void shouldUpdate() throws Exception {
        UserDto updatedUser = getUpdatedDto();

        ResultActions resultActions = mockMvc.perform(put(REST_URL + "/" + USER_DTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.objectToJson(updatedUser))
                        .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        UserDto actual = USER_DTO_MATCHER.readFromJson(resultActions);
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(getUpdatedDto());
        assertThat(actual).isEqualTo(mapper.toDto(userService.findById(getUpdatedDto().getId())));
    }

    @Test
    void shouldGetProfile() throws Exception {
        mockMvc.perform(get(REST_URL + "/profile")
                        .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_DTO_MATCHER.contentJson(USER_DTO));
    }

    @Test
    void shouldUpdateProfile() throws Exception {
        UserDto updatedUser = getUpdatedDto();

        ResultActions resultActions = mockMvc.perform(put(REST_URL + "/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.objectToJson(updatedUser))
                        .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        UserDto actual = USER_DTO_MATCHER.readFromJson(resultActions);
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(getUpdatedDto());
        assertThat(actual).isEqualTo(mapper.toDto(userService.findById(getUpdatedDto().getId())));
    }

    @Test
    void shouldCreateUnprocessable() throws Exception {
        UserDto newUserDto = new UserDto();
        newUserDto.setEmail("mail");

        mockMvc.perform(post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.objectToJson(newUserDto))
                        .with(userHttpBasic(ADMIN)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.error").value("Unprocessable Entity"))
                .andExpect(jsonPath("$.message").value(USER_CONSTRAINTS_MATCHER));
    }

    @Test
    void shouldUpdateUnprocessable() throws Exception {
        UserDto newUserDto = new UserDto();
        newUserDto.setEmail("mail");

        mockMvc.perform(put(REST_URL + "/" + USER_DTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.objectToJson(newUserDto))
                        .with(userHttpBasic(ADMIN)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.error").value("Unprocessable Entity"))
                .andExpect(jsonPath("$.message").value(USER_CONSTRAINTS_MATCHER));
    }

    @Test
    void shouldCreateDuplicate() throws Exception {
        UserDto newUserDto = getNewDto();
        newUserDto.setEmail(USER_DTO.getEmail());

        mockMvc.perform(post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.objectToJson(newUserDto))
                        .with(userHttpBasic(ADMIN)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.error").value("Unprocessable Entity"))
                .andExpect(jsonPath("$.message").value("User with this email already exists"));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void shouldUpdateDuplicate() throws Exception {
        UserDto newUserDto = getUpdatedDto();
        newUserDto.setEmail(ADMIN_DTO.getEmail());

        mockMvc.perform(put(REST_URL + "/" + USER_DTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.objectToJson(newUserDto))
                        .with(userHttpBasic(ADMIN)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.error").value("Unprocessable Entity"))
                .andExpect(jsonPath("$.message").value("User with this email already exists"));
    }

    @Test
    void shouldNotProcessUnsafeHtml() throws Exception {
        UserDto newUserDto = getNewDto();
        newUserDto.setLastName("<script>alert xss /script>");

        mockMvc.perform(put(REST_URL + "/" + USER_DTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.objectToJson(newUserDto))
                        .with(userHttpBasic(ADMIN)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.error").value("Unprocessable Entity"))
                .andExpect(jsonPath("$.message").value("[lastName] Invalid field value"));
    }
}
