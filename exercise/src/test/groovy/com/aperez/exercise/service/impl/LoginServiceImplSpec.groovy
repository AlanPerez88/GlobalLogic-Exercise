package com.aperez.exercise.service.impl

import com.aperez.exercise.dto.UserDto
import com.aperez.exercise.entity.User
import com.aperez.exercise.exception.JwtException
import com.aperez.exercise.repository.UserRepository
import com.aperez.exercise.util.Jwt
import io.jsonwebtoken.ExpiredJwtException
import org.modelmapper.ModelMapper
import spock.lang.Specification

class LoginServiceImplSpec extends Specification {
    LoginServiceImpl loginServiceImpl = new LoginServiceImpl()
    UserRepository userRepository
    ModelMapper modelMapper = new ModelMapper()
    Jwt jwtUtil = new Jwt()

    def setup(){
        userRepository = Stub(UserRepository)
        loginServiceImpl.userRepository = userRepository
        loginServiceImpl.modelMapper = modelMapper
        loginServiceImpl.jwtUtil = jwtUtil
        jwtUtil.expirationTime = 1L;

    }

    def "login"(){
        given:
        String email = "pedro@test.com"
        String password = "123Aa"
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZWRyb0B0ZXN0LmNvbSIsInBhc3N3b3JkIjoiMTIzQWEiLCJpc3MiOiJjb20uYXBlcmV6LmV4ZXJjaXNlIiwiZXhwIjoyMTQyNDU2ODAyfQ.iCPhB_j-R9mFWeK-BeIdMz2h52h9Uh7caAYifvfD7Mg"
        userRepository.findByEmailPassword(email,password) >> user_def(email, password)

        when:
        def resp = loginServiceImpl.login(modelMapper.map(user_def(email,password), UserDto.class), token )

        then:
        resp != null
        resp instanceof UserDto

    }

    def "loginNotUser"(){
        given:
        String email = "pedro@test.com"
        String password = "123Aa"
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZWRyb0B0ZXN0LmNvbSIsInBhc3N3b3JkIjoiMTIzQWEiLCJpc3MiOiJjb20uYXBlcmV6LmV4ZXJjaXNlIiwiZXhwIjoyMTQyNDU2ODAyfQ.iCPhB_j-R9mFWeK-BeIdMz2h52h9Uh7caAYifvfD7Mg"
        userRepository.findByEmailPassword(email,password) >> null

        when:
        loginServiceImpl.login(modelMapper.map(user_def(email,password), UserDto.class), token )

        then:
        thrown(JwtException)

    }

    def "logout"(){
        given:
        String email = "pedro@test.com"
        String password = "123Aa"
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZWRyb0B0ZXN0LmNvbSIsInBhc3N3b3JkIjoiMTIzQWEiLCJpc3MiOiJjb20uYXBlcmV6LmV4ZXJjaXNlIiwiZXhwIjoyMTQyNDU2ODAyfQ.iCPhB_j-R9mFWeK-BeIdMz2h52h9Uh7caAYifvfD7Mg"
        userRepository.findByEmailPassword(email,password) >> user_def(email, password)

        when:
        def resp = loginServiceImpl.logout(modelMapper.map(user_def(email,password), UserDto.class), token )

        then:
        resp != null
        resp instanceof UserDto

    }



    def "loginTokenExpired"(){
        given:
        String email = "asd@asd.cl"
        String password = "123asdASD"
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGFuQGFsYW4uY29tIiwicGFzc3dvcmQiOiIxMjNhc0QiLCJpc3MiOiJjb20uYXBlcmV6LmV4ZXJjaXNlIiwiZXhwIjoxNjAyMzkxMzEwfQ.qIJLzYFzq0QDrdl3VIeSPjVXx3o8oDLqcWLJmst1RXY"
        userRepository.findByEmailPassword(email,password) >> user_def(email, password)

        when:
        loginServiceImpl.login(modelMapper.map(user_def(email,password), UserDto.class), token )

        then:
        thrown(ExpiredJwtException)

    }



    def "loguotTokenExpired"(){
        given:
        String email = "asd@asd.cl"
        String password = "123asdASD"
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGFuQGFsYW4uY29tIiwicGFzc3dvcmQiOiIxMjNhc0QiLCJpc3MiOiJjb20uYXBlcmV6LmV4ZXJjaXNlIiwiZXhwIjoxNjAyMzkxMzEwfQ.qIJLzYFzq0QDrdl3VIeSPjVXx3o8oDLqcWLJmst1RXY"
        userRepository.findByEmailPassword(email,password) >> user_def(email, password)

        when:
        loginServiceImpl.logout(modelMapper.map(user_def(email,password), UserDto.class), token )

        then:
        thrown(ExpiredJwtException)

    }



    def "getToken"(){
        given:
        String email = "asdL@asdL.cl"
        String password = "123asdASDLP"
        userRepository.findByEmailPassword(email,password) >> user_def(email, password)

        when:
        def resp = loginServiceImpl.getToken(modelMapper.map(user_def(email,password), UserDto.class) )

        then:
        resp != null
        resp instanceof UserDto

    }


    def "getTokenNotUser"(){
        given:
        String email = "asdL@asdL.cl"
        String password = "123asdASDLP"
        userRepository.findByEmailPassword(email,password) >> null

        when:
        loginServiceImpl.getToken(modelMapper.map(user_def(email,password), UserDto.class) )

        then:
        thrown(JwtException)

    }


    def "user_def"( String email, String password){
        User user = new User()
        user.email = email
        user.password = password
        return user
    }

}
