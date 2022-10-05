package com.inn.store.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashbordStatistic {
    long category;
    long product;
    long bill;
}
