package com.project.sportsgeek.controller;

import com.project.sportsgeek.model.profile.Role;
import com.project.sportsgeek.response.Result;
import com.project.sportsgeek.service.RoleService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/roles", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoleController {

    @Autowired
    RoleService roleService;

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"), @ApiResponse(code = 404, message = "Bad Request")})
    @PreAuthorize("hasRole('Admin')")
    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        Result<List<Role>> roleList = roleService.findAllRole();
        return new ResponseEntity<>(roleList.getData(), HttpStatus.valueOf(roleList.getCode()));
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"), @ApiResponse(code = 404, message = "Bad Request")})
    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/{roleId}")
    public ResponseEntity<Role> getRoleById(@PathVariable int roleId) throws Exception {
        Result<Role> roleList = roleService.findRoleById(roleId);
        return new ResponseEntity<>(roleList.getData(), HttpStatus.valueOf(roleList.getCode()));
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"), @ApiResponse(code = 404, message = "Bad Request")})
    @PreAuthorize("hasRole('Admin')")
    @PostMapping
    public ResponseEntity<Role> addRole(@RequestBody(required = true) Role role) throws Exception {
        Result<Role> roleResult = roleService.addRole(role);
        return new ResponseEntity(roleResult.getData(), HttpStatus.valueOf(roleResult.getCode()));
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated schema"), @ApiResponse(code = 404, message = "Schema not found"), @ApiResponse(code = 400, message = "Missing or invalid request body"), @ApiResponse(code = 500, message = "Internal error")})
    @PreAuthorize("hasRole('Admin')")
    @PutMapping("/{roleId}")
    public ResponseEntity<Role> updateRole(@PathVariable int roleId, @RequestBody(required = true) Role role) throws Exception {
        Result<Role> roleResult = roleService.updateRole(roleId, role);
        return new ResponseEntity(roleResult.getData(), HttpStatus.valueOf(roleResult.getCode()));
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully deleted schema"), @ApiResponse(code = 404, message = "Schema not found"), @ApiResponse(code = 409, message = "Schema is in use"), @ApiResponse(code = 500, message = "Error deleting schema")})
    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/{roleId}")
    public ResponseEntity<Result<String>> deleteRoleById(@PathVariable int roleId) throws Exception {
        Result<String> result = roleService.deleteRole(roleId);
        return new ResponseEntity(result, HttpStatus.valueOf(result.getCode()));
    }
}
