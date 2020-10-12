package com.aperez.exercise.controller

import com.aperez.exercise.dto.UserDto
import com.aperez.exercise.service.LoginService
import org.springframework.http.ResponseEntity
import spock.lang.Specification

class LoginControllerSpec extends Specification {
    LoginController loginController = new LoginController()
    LoginService loginService

    def setup(){
        loginService = Stub(LoginService)
        loginController.loginService = loginService
    }

    def "login"(){
        given:
        String email = "asd@asd.cl"
        String password = "123asdASD"
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGFuQGFsYW4uY29tIiwicGFzc3dvcmQiOiIxMjNhc0QiLCJpc3MiOiJjb20uYXBlcmV6LmV4ZXJjaXNlIiwiZXhwIjoxNjAyMzkxMzEwfQ.qIJLzYFzq0QDrdl3VIeSPjVXx3o8oDLqcWLJmst1RXY"
        loginService.login(_ as UserDto, token) >> userdto_def(email, password)

        when:
        def result = loginController.login(userdto_def(email, password), token)

        then:
        result instanceof ResponseEntity

    }

    def "logout"(){
        given:
        String email = "asd@asd.cl"
        String password = "123asdASD"
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGFuQGFsYW4uY29tIiwicGFzc3dvcmQiOiIxMjNhc0QiLCJpc3MiOiJjb20uYXBlcmV6LmV4ZXJjaXNlIiwiZXhwIjoxNjAyMzkxMzEwfQ.qIJLzYFzq0QDrdl3VIeSPjVXx3o8oDLqcWLJmst1RXY"
        loginService.logout(_ as UserDto, token) >> userdto_def(email, password)

        when:
        def result = loginController.logout(userdto_def(email, password), token)

        then:
        result instanceof ResponseEntity

    }

    def "getToken"(){
        given:
        String email = "asd@asd.cl"
        String password = "123asdASD"
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGFuQGFsYW4uY29tIiwicGFzc3dvcmQiOiIxMjNhc0QiLCJpc3MiOiJjb20uYXBlcmV6LmV4ZXJjaXNlIiwiZXhwIjoxNjAyMzkxMzEwfQ.qIJLzYFzq0QDrdl3VIeSPjVXx3o8oDLqcWLJmst1RXY"
        loginService.getToken(_ as UserDto) >> userdto_def(email, password)

        when:
        def result = loginController.getToken(userdto_def(email, password))

        then:
        result instanceof ResponseEntity

    }


    def "userdto_def"(String email, String password) {
        UserDto userDto = new UserDto()
        userDto.password = email
        userDto.email = password
        return userDto
    }
}
