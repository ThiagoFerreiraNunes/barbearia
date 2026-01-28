package com.api.barbershop.service;

import com.api.barbershop.dto.plan.GetPlanDTO;
import com.api.barbershop.dto.plan.PostPlanDTO;
import com.api.barbershop.dto.plan.PutPlanDTO;
import com.api.barbershop.exeption.BusinessRuleException;
import com.api.barbershop.model.Plan;
import com.api.barbershop.repository.PlanRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {
    @Autowired PlanRepository planRepository;

    @Transactional
    public GetPlanDTO postPlan(PostPlanDTO data){
        Plan plan = new Plan(data);
        planRepository.save(plan);
        return new GetPlanDTO(plan);
    }

    public List<GetPlanDTO> getAllPlans(){
        return planRepository.findByAvailableAndSortByPrice().stream().map(GetPlanDTO::new).toList();
    }

    public GetPlanDTO getPlanById(Long id){
        Plan plan = planRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Plan not found."));

        if(Boolean.FALSE.equals(plan.getIsAvailable())){
            throw new BusinessRuleException("Plan is deleted.");
        }

        return new GetPlanDTO(plan);
    }

    @Transactional
    public GetPlanDTO putPlanById(Long id, PutPlanDTO data){
        Plan plan = planRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Plan not found."));

        if(Boolean.FALSE.equals(plan.getIsAvailable())){
            throw new BusinessRuleException("Plan is deleted.");
        }

        plan.update(data);
        return new GetPlanDTO(plan);
    }

    @Transactional
    public void deletePlanById(Long id){
        Plan plan = planRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Plan not found."));

        if(Boolean.FALSE.equals(plan.getIsAvailable())){
            throw new BusinessRuleException("Plan is already deleted.");
        }

        plan.delete();
    }

    @Transactional
    public GetPlanDTO reactivatePlanById(Long id){
        Plan plan = planRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Plan not found."));

        if(Boolean.TRUE.equals(plan.getIsAvailable())){
            throw new BusinessRuleException("Plan is already activate.");
        }

        return new GetPlanDTO(plan);
    }
}
