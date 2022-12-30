package ru.jsft.voteforlunch.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.jsft.voteforlunch.AbstractSpringBootTest;
import ru.jsft.voteforlunch.error.IllegalRequestDataException;
import ru.jsft.voteforlunch.model.Role;
import ru.jsft.voteforlunch.service.UserService;
import ru.jsft.voteforlunch.utils.LinkedHashMapMatcher;
import ru.jsft.voteforlunch.web.controller.dto.UserDto;
import ru.jsft.voteforlunch.web.controller.mapper.UserMapper;
import ru.jsft.voteforlunch.web.json.JsonUtil;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.jsft.voteforlunch.testdata.UserTestData.*;
import static ru.jsft.voteforlunch.web.controller.UserController.REST_URL;

class UserControllerTest extends AbstractSpringBootTest {
    private static final LinkedHashMapMatcher USER_CONSTRAINTS_MATCHER = new LinkedHashMapMatcher(new LinkedHashMap<>(Map.of(
                    "email", "Please enter valid e-mail",
                    "firstName", "First name must not be empty",
                    "lastName", "Last name must not be empty",
                    "roles", "Roles must not be empty",
                    "password", "Password must not be empty")));

    private static final LinkedHashMapMatcher UNSAFE_LASTNAME_MATCHER = new LinkedHashMapMatcher(new LinkedHashMap<>(Map.of(
                    "lastName", "Invalid field value")));

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper mapper;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllUsers() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_DTO_MATCHER.contentJson(USER_DTO_LIST));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getUser() throws Exception {
        mockMvc.perform(get(REST_URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_DTO_MATCHER.contentJson(ADMIN_DTO));
    }

    @Test
    void getUnauthorized() throws Exception {
        mockMvc.perform(get(REST_URL + "/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getForbidden() throws Exception {
        mockMvc.perform(get(REST_URL + "/1")).andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createUser() throws Exception {
        UserDto newUser = getNewDto();

        ResultActions resultActions = mockMvc.perform(post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.objectToJson(newUser)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        UserDto actual = USER_DTO_MATCHER.readFromJson(resultActions);
        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(newUser);
        assertThat(actual).usingRecursiveComparison().ignoringFields("password").isEqualTo(userService.findById(actual.getId()));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteUser() throws Exception {
        mockMvc.perform(delete(REST_URL + "/1")).andExpect(status().isNoContent());

        assertThrows(IllegalRequestDataException.class, () -> userService.findById(1L));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateUser() throws Exception {
        UserDto updatedUser = getUpdatedDto();

        ResultActions resultActions = mockMvc.perform(put(REST_URL + "/" + USER_DTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.objectToJson(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        UserDto actual = USER_DTO_MATCHER.readFromJson(resultActions);
        assertThat(actual).isEqualTo(getUpdatedDto());
        assertThat(actual).isEqualTo(mapper.toDto(userService.findById(getUpdatedDto().getId())));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getUserProfile() throws Exception {
        mockMvc.perform(get(REST_URL + "/profile"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_DTO_MATCHER.contentJson(USER_DTO));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateUserProfile() throws Exception {
        UserDto updatedUser = getUpdatedDto();

        ResultActions resultActions = mockMvc.perform(put(REST_URL + "/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.objectToJson(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        UserDto actual = USER_DTO_MATCHER.readFromJson(resultActions);
        assertThat(actual).isEqualTo(getUpdatedDto());
        assertThat(actual).isEqualTo(mapper.toDto(userService.findById(getUpdatedDto().getId())));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createUnprocessable() throws Exception {
        UserDto newUserDto = new UserDto(null, "mail", "", "", "", true, Set.of());

        mockMvc.perform(post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.objectToJson(newUserDto)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.invalid_params").value(USER_CONSTRAINTS_MATCHER));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateUnprocessable() throws Exception {
        UserDto newUserDto = new UserDto(null, "mail", "", "", "", true, Set.of());

        mockMvc.perform(put(REST_URL + "/" + USER_DTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.objectToJson(newUserDto)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.invalid_params").value(USER_CONSTRAINTS_MATCHER));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createDuplicate() throws Exception {
        UserDto newUserDto = getNewDto(USER_DTO.getEmail());

        mockMvc.perform(post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.objectToJson(newUserDto)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.detail").value("User with this email already exists"));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    @WithUserDetails(value = ADMIN_MAIL)
    void updateDuplicate() throws Exception {
        UserDto newUserDto = getUpdatedDto(ADMIN_DTO.getEmail());

        mockMvc.perform(put(REST_URL + "/" + USER_DTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.objectToJson(newUserDto)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.detail").value("User with this email already exists"));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void notProcessUnsafeHtml() throws Exception {
        UserDto newUserDto = new UserDto(
                null,
                "mail@mail.ru", "name",
                "<script> Alert xss!!! /script>",
                "password", true, Set.of(Role.USER));

        mockMvc.perform(put(REST_URL + "/" + USER_DTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.objectToJson(newUserDto)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.invalid_params").value(UNSAFE_LASTNAME_MATCHER));
    }
}
