package com.aperez.exercise.controller

import com.aperez.exercise.dto.PhoneDto
import com.aperez.exercise.dto.UserDto
import com.aperez.exercise.service.UserService
import org.springframework.http.ResponseEntity
import spock.lang.Specification

class UserControllerSpec extends Specification {
    UserController userController = new UserController()
    UserService userService

    def setup() {
        userService = Stub(UserService)
        userController.userService = userService
    }


    def "save"() {
        given:
        String name = "pedro"
        String email = "asd@asd.cl"
        String password = "123asdASD"
        userService.save(_ as UserDto) >> userdto_def(name, email, password)

        when:
        def result = userController.save(userdto_def(name, email, password))

        then:
        result instanceof ResponseEntity

    }



    def "update"() {
        given:
        String name = "pedro"
        String email = "asd@asd.cl"
        String password = "123asdASD"
        userService.update(_ as UserDto) >> userdto_def(name, email, password)

        when:
        def result = userController.update(userdto_def(name, email, password))

        then:
        result instanceof ResponseEntity

    }

    def "userdto_def"(String name, String email, String password) {
        UserDto userDto = new UserDto()
        userDto.name = name
        userDto.password = email
        userDto.email = password
        userDto.phones = phonedto_def() as Set<PhoneDto>
        return userDto
    }

    def "phonedto_def"() {
        Set<PhoneDto> phones = new HashSet<>()
        PhoneDto phone = new PhoneDto()
        phone.number = 1234567
        phone.countrycode = 2
        phone.citycode = 7
        phones.add(phone)
        return phones
    }
}
