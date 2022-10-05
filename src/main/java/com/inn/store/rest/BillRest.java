package com.inn.store.rest;

import com.inn.store.entities.Bill;
import com.inn.store.request.BillRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@RequestMapping(path = "/bill")
public interface BillRest {

    @PostMapping(path = "/generateRaport")
    ResponseEntity<String> generateRaport(@RequestBody BillRequest billRequest);

    @GetMapping
    ResponseEntity<List<Bill>> getBills();

    @PostMapping(path="/getPdf")
    ResponseEntity<byte[]> getPdf(@RequestBody Map<String , Object> requestMap);

    @DeleteMapping(path = "/{id}")
    ResponseEntity<String> deleteBill(@PathVariable Integer id);
}
