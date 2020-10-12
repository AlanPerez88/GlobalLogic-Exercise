package com.aperez.exercise.service.impl

import com.aperez.exercise.dto.PhoneDto
import com.aperez.exercise.dto.UserDto
import com.aperez.exercise.entity.Phone
import com.aperez.exercise.entity.User
import com.aperez.exercise.exception.UserException
import com.aperez.exercise.repository.UserRepository
import com.aperez.exercise.util.Jwt
import org.modelmapper.ModelMapper
import spock.lang.Specification

class UserServiceImplSpec extends Specification {
    //HappyPath and SadPath
    UserServiceImpl userServiceImpl = new UserServiceImpl()
    UserRepository userRepository
    ModelMapper modelMapper = new ModelMapper()
    Jwt jwtUtil = new Jwt()

    def setup(){
        userRepository = Stub(UserRepository)
        userServiceImpl.userRepository = userRepository
        userServiceImpl.modelMapper = modelMapper
        userServiceImpl.jwtUtil = jwtUtil
        jwtUtil.expirationTime = 1L;

    }

    def "save"(){
        given:
        String name = "pedro"
        String email = "asd@asd.cl"
        String password = "123asdASD"
        userRepository.save(_ as User ) >> user_def(name, email, password)

        when:
        def resp = userServiceImpl.save(modelMapper.map(user_def(name, email, password), UserDto.class))

        then:
        //thrown(NullPointerException)
        resp != null
        resp instanceof UserDto
    }

    def "update"(){
        given:
        Long id = 1
        String name = "pedro"
        String email = "asd@asd.cl"
        String password = "123asdASD"
        userServiceImpl.findById(id) >> user_update_def(id, name, email, password)
        userRepository.save(_ as User ) >> user_update_def(id, name, email, password)

        when:
        def resp = userServiceImpl.update(modelMapper.map(user_update_def(id, name, email, password), UserDto.class))

        then:
        thrown(UserException)


    }


    def "user_def"(String name, String email, String password){
        User user = new User()
        user.name = name
        user.email = email
        user.password = password
        user.phones = phones_def() as Set<Phone>
        return user
    }

    def "phones_def"(){
        Set<Phone> phones = new HashSet<>()
        Phone phone = new Phone()
        phone.number = 1234567
        phone.countrycode = 2
        phone.citycode = 7
        phones.add(phone)
        return phones
    }

    def "user_update_def"(Long id, String name, String email, String password){
        User user = new User()
        user.id = id
        user.name = name
        user.email = email
        user.password = password
        user.phones = phones_def() as Set<Phone>
        return user
    }

    def "phones_update_def"(){
        Set<Phone> phones = new HashSet<>()
        Phone phone = new Phone()
        phone.id = 1
        phone.number = 1234567
        phone.countrycode = 2
        phone.citycode = 7
        phones.add(phone)
        return phones
    }




}
