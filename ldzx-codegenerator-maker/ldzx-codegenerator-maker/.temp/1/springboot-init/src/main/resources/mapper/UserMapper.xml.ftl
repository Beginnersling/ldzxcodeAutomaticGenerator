<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${basePackage}.springbootinit.mapper.UserMapper">
    <resultMap id="BaseResultMap" type="${basePackage}.springbootinit.model.entity.User">
        <id property="id" column="id" jdbcType="BIGINT"/>
        <result property="userName" column="userName" jdbcType="VARCHAR"/>
        <result property="userAvatar" column="userAvatar" jdbcType="VARCHAR"/>
        <result property="userProfile" column="userProfile" jdbcType="VARCHAR"/>
        <result property="userRole" column="userRole" jdbcType="VARCHAR"/>
        <result property="createTime" column="createTime" jdbcType="TIMESTAMP"/>
        <result property="updateTime" column="updateTime" jdbcType="TIMESTAMP"/>
        <result property="isDelete" column="isDelete" jdbcType="TINYINT"/>
    </resultMap>

    <sql id="Base_Column_List">
        id,userName,userAvatar,userProfile,
        userRole,createTime,updateTime,isDelete
    </sql>
</mapper>
