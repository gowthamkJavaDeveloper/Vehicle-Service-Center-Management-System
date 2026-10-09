package com.vsc.vehicleservicecenter.jobcard.controller;
import java.util.*;import org.springframework.http.*;import org.springframework.web.bind.annotation.*;import jakarta.validation.Valid;import lombok.RequiredArgsConstructor;
import com.vsc.vehicleservicecenter.jobcard.service.JobCardService;import com.vsc.vehicleservicecenter.jobcard.dto.JobCardRequest;import com.vsc.vehicleservicecenter.jobcard.entity.JobCard;
@RestController @RequestMapping("/api/v1/job-cards") @RequiredArgsConstructor public class JobCardController {
 private final JobCardService service;
 @PostMapping public ResponseEntity<JobCard> create(@Valid @RequestBody JobCardRequest r){return ResponseEntity.status(201).body(service.create(r));}
 @GetMapping public List<JobCard> all(){return service.all();}
 @GetMapping("/{id}") public JobCard get(@PathVariable Long id){return service.get(id);}
 @PutMapping("/{id}") public JobCard update(@PathVariable Long id,@Valid @RequestBody JobCardRequest r){return service.update(id,r);}
 @PatchMapping("/{id}/complete") public JobCard close(@PathVariable Long id){return service.close(id);}
}
