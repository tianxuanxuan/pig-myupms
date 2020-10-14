/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.pig4cloud.pig.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.dto.UserInfo;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.pig4cloud.pig.admin.service.SysUserService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;


/**
 * 用户表
 *
 * @author pig code generator
 * @date 2020-10-13 13:48:21
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sysuser" )
@Api(value = "sysuser", tags = "用户表管理")
public class SysUserController {

    private final SysUserService sysUserService;

    /**
     * 获取当前用户全部信息
     * @return
     */
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    @GetMapping(value = "/info")
    public R<UserInfo> info(){
        String userName = SecurityUtils.getUser().getUsername();
        SysUser user = sysUserService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getUsername, userName));
        if (user == null){
            return R.failed("获取当前用户信息失败");
        }
        UserInfo userInfo = sysUserService.getUserInfo(user);
        return R.ok(userInfo);
    }

    /**
     * 分页查询
     * @param page 分页对象
     * @param sysUser 用户表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('generator_sysuser_get')" )
    public R getSysUserPage(Page page, SysUser sysUser) {
        return R.ok(sysUserService.page(page, Wrappers.query(sysUser)));
    }


    /**
     * 通过id查询用户表
     * @param userId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{userId}" )
    @PreAuthorize("@pms.hasPermission('generator_sysuser_get')" )
    public R getById(@PathVariable("userId" ) Integer userId) {
        return R.ok(sysUserService.getById(userId));
    }

    /**
     * 新增用户表
     * @param sysUser 用户表
     * @return R
     */
    @ApiOperation(value = "新增用户表", notes = "新增用户表")
    @SysLog("新增用户表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('generator_sysuser_add')" )
    public R save(@RequestBody SysUser sysUser) {
        return R.ok(sysUserService.save(sysUser));
    }

    /**
     * 修改用户表
     * @param sysUser 用户表
     * @return R
     */
    @ApiOperation(value = "修改用户表", notes = "修改用户表")
    @SysLog("修改用户表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('generator_sysuser_edit')" )
    public R updateById(@RequestBody SysUser sysUser) {
        return R.ok(sysUserService.updateById(sysUser));
    }

    /**
     * 通过id删除用户表
     * @param userId id
     * @return R
     */
    @ApiOperation(value = "通过id删除用户表", notes = "通过id删除用户表")
    @SysLog("通过id删除用户表" )
    @DeleteMapping("/{userId}" )
    @PreAuthorize("@pms.hasPermission('generator_sysuser_del')" )
    public R removeById(@PathVariable Integer userId) {
        return R.ok(sysUserService.removeById(userId));
    }

}
