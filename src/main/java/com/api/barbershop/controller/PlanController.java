package com.api.barbershop.controller;

import com.api.barbershop.dto.plan.GetPlanDTO;
import com.api.barbershop.dto.plan.PostPlanDTO;
import com.api.barbershop.dto.plan.PutPlanDTO;
import com.api.barbershop.service.PlanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/plans")
public class PlanController {
    @Autowired PlanService planService;

    @PostMapping
    public ResponseEntity<GetPlanDTO> postPlan(@RequestBody @Valid PostPlanDTO data, UriComponentsBuilder builder){
        GetPlanDTO plan = planService.postPlan(data);
        URI uri = builder.path("/{id}").buildAndExpand(plan.id()).toUri();
        return ResponseEntity.created(uri).body(plan);
    }

    @GetMapping
    public ResponseEntity<List<GetPlanDTO>> getAllPlans(){
        return ResponseEntity.ok(planService.getAllPlans());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPlanDTO> getPlanById(@PathVariable Long id){
        return ResponseEntity.ok(planService.getPlanById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetPlanDTO> putPlanById(@PathVariable Long id, @RequestBody PutPlanDTO data){
        return ResponseEntity.ok(planService.putPlanById(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlanById(@PathVariable Long id){
        planService.deletePlanById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GetPlanDTO> reactivatePlanById(@PathVariable Long id){
        return ResponseEntity.ok(planService.reactivatePlanById(id));
    }
}
