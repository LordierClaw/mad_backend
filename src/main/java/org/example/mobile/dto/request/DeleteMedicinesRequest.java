package org.example.mobile.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteMedicinesRequest {
    @NotEmpty(message = "Danh sách ID thuốc không được trống")
    private List<Long> ids;
} 