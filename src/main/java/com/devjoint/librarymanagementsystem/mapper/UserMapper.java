package com.devjoint.librarymanagementsystem.mapper;

import com.devjoint.librarymanagementsystem.dto.request.RegisterRequest;
import com.devjoint.librarymanagementsystem.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toEntity(RegisterRequest requestDto);

}