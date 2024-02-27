package com.ej.hgj.service.menu;

import com.ej.hgj.entity.menu.Menu;

import java.util.List;

public interface MenuService {

    List<Menu> menuList(Menu menu);

    List<Menu> getMenuList(Menu menu);

    List<Menu> getUserMenuList(String staffId);

    List<Menu> findMenuByRoleId(String roleId);

}
