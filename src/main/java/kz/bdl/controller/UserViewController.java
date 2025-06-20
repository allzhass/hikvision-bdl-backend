package kz.bdl.controller;

import kz.bdl.dto.UserDTO;
import kz.bdl.service.UserService;
import kz.bdl.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user-view")
@Slf4j
public class UserViewController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping
    public String getAllUsers(Model model) {
        log.info("getAllUsers start");
        model.addAttribute("users", userService.getAllUsers());
        log.info("getAllUsers end");
        return "user/list";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable Long id, Model model) {
        log.info("getUserById start: {}", id);
        model.addAttribute("user", userService.getUserById(id));
        log.info("getUserById end");
        return "user/view";
    }

    @GetMapping("/active")
    public String getActiveUsers(Model model) {
        log.info("getActiveUsers start");
        model.addAttribute("users", userService.getActiveUsers());
        log.info("getActiveUsers end");
        return "user/list";
    }

    @GetMapping("/role/{roleId}")
    public String getUsersByRoleId(@PathVariable Long roleId, Model model) {
        log.info("getUsersByRoleId start: {}", roleId);
        model.addAttribute("users", userService.getUsersByRoleId(roleId));
        log.info("getUsersByRoleId end");
        return "user/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        log.info("showAddForm start");
        model.addAttribute("user", new UserDTO());
        model.addAttribute("roles", roleService.getAllRoles());
        log.info("showAddForm end");
        return "user/add";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute UserDTO userDTO) {
        log.info("addUser start: {}", userDTO);
        userService.addUser(userDTO);
        log.info("addUser end");
        return "redirect:/user-view";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        log.info("showEditForm start: {}", id);
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleService.getAllRoles());
        log.info("showEditForm end");
        return "user/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute UserDTO userDTO) {
        log.info("updateUser start: {}", id);
        userDTO.setId(id);
        userService.updateUser(userDTO);
        log.info("updateUser end");
        return "redirect:/user-view";
    }

    @PostMapping("/{id}/activate")
    public String activateUser(@PathVariable Long id) {
        log.info("activateUser start: {}", id);
        userService.activateUser(id);
        log.info("activateUser end");
        return "redirect:/user-view";
    }

    @PostMapping("/{id}/deactivate")
    public String deactivateUser(@PathVariable Long id) {
        log.info("deactivateUser start: {}", id);
        userService.deactivateUser(id);
        log.info("deactivateUser end");
        return "redirect:/user-view";
    }
} 