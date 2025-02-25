package com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.dao;

public interface UserName {
  String getFirstName();
  String getLastName();
  String getUserId();

default String getFullName() { return getFirstName() + " " + getLastName(); }
}
