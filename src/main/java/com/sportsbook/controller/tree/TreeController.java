package com.sportsbook.controller.tree;

import com.sportsbook.dto.tree.SportTreeDTO;
import com.sportsbook.service.tree.TreeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tree")
public class TreeController {

    private final TreeService treeService;

    public TreeController(TreeService treeService) {
        this.treeService = treeService;
    }

    @GetMapping
    public List<SportTreeDTO> getTree(@RequestParam(required = false) Integer hours) {
        return treeService.getTree(hours);
    }
}
