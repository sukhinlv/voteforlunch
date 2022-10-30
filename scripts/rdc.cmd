REM Run docker postgres test container "VoteForLunch_db"
REM db name = voteforlunch
REM user is "user" and password is "password"
REM local port is 5434
docker run -p 5434:5432 -d --name VoteForLunch_db -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=voteforlunch postgres
