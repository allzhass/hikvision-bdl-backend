package kz.bdl.controller;

import kz.bdl.dto.RoleDTO;
import kz.bdl.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/role-view")
@Slf4j
public class RoleViewController {
    @Autowired
    private RoleService roleService;

    @GetMapping
    public String getAllRoles(Model model) {
        log.info("getAllRoles start");
        model.addAttribute("roles", roleService.getAllRoles());
        log.info("getAllRoles end");
        return "role/list";
    }

    @GetMapping("/{id}")
    public String getRoleById(@PathVariable Long id, Model model) {
        log.info("getRoleById start: {}", id);
        model.addAttribute("role", roleService.getRoleById(id));
        log.info("getRoleById end");
        return "role/view";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        log.info("showAddForm start");
        model.addAttribute("role", new RoleDTO());
        log.info("showAddForm end");
        return "role/add";
    }

    @PostMapping("/add")
    public String addRole(@ModelAttribute RoleDTO roleDTO) {
        log.info("addRole start: {}", roleDTO);
        roleService.addRole(roleDTO);
        log.info("addRole end");
        return "redirect:/role-view";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        log.info("showEditForm start: {}", id);
        model.addAttribute("role", roleService.getRoleById(id));
        log.info("showEditForm end");
        return "role/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateRole(@PathVariable Long id, @ModelAttribute RoleDTO roleDTO) {
        log.info("updateRole start: {}", id);
        roleDTO.setId(id);
        roleService.updateRole(roleDTO);
        log.info("updateRole end");
        return "redirect:/role-view";
    }
} 