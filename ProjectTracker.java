package com.redwoods.ProjectTracker.models;

import jakarta.persistence.*;

@Entity
public class ProjectTracker{

   private String costEstimated;
   private String createdBy;
   private String customerId;
   private String criticality;
   private String description;
   private String costActual;
   private String id;
   private String validFrom;
   private String validTo;
   private String status;
}