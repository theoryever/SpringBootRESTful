package com.loveqh.restful.controller;

import com.loveqh.restful.entity.User;
import com.loveqh.restful.exception.ServerErrorException;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    // 创建线程安全的Map
    static Map<Long, User> users = Collections.synchronizedMap(new HashMap<Long, User>());

    @ApiOperation(value = "获取所有用户", notes = "获取所有用户信息列表")
    @RequestMapping(value = "", method= RequestMethod.GET)
    public List<User> getUserList() {
        List<User> userList = new ArrayList<>(users.values());
        return userList;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "添加用户", notes = "根据请求体的user信息添加新用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User", paramType = "body")
    @RequestMapping(value="/", method=RequestMethod.POST)
    public User postUser(@RequestBody User user) {
        users.put(user.getId(), user);
        return user;
    }

    @ApiOperation(value = "获取指定id的用户", notes = "获取指定id的用户")
    @ApiImplicitParam(name = "id", value = "要查询的用户id", allowableValues = "range[0, 10)", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public User getUser(@PathVariable Long id) {
        if(id == 100) {
            throw new ServerErrorException();
        }
        return users.get(id);
    }

    @ApiOperation(value = "更新用户信息", notes = "更新指定id的用户信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "要更新的用户id", required = true, dataType = "Long", paramType = "path"),
        @ApiImplicitParam(name = "userUpdate", value = "要更新的用户信息", required = true, dataType = "User", paramType = "body")
    })
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public User putUser(@PathVariable Long id, @RequestBody User userUpdate) {
        User user = users.get(id);
        user.setUsername(userUpdate.getUsername());
        user.setAddress(userUpdate.getAddress());
        users.put(id, user);
        return user;
    }

    @ApiOperation(value = "删除用户", notes = "删除指定id的用户")
    @ApiImplicitParam(name = "id", value = "要删除的用户id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long id) {
        users.remove(id);
        return "success";
    }

    @ExceptionHandler(ServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String serverError(ServerErrorException e) {
        return "error" + e.getClass().getName();
    }
}
