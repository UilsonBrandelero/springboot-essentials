/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package springboot.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import springboot.domain.Anime;
import springboot.requests.AnimePostRequestBody;
import springboot.requests.AnimePutRequestBody;
import springboot.service.AnimeService;



/**
 *
 * @author uilson
 */
@RestController
@RequestMapping("animes")
@RequiredArgsConstructor
@Log4j2

public class AnimeController {
    private final AnimeService animeService;
    
    @GetMapping("list")
    public ResponseEntity<Page<Anime>> list(Pageable pageable) {
        
        return new ResponseEntity<>(animeService.listAll(pageable), HttpStatus.OK);
    }
    @GetMapping("list-user-details")
    public ResponseEntity<Page<Anime>> listUserDetails(Pageable pageable,
                                        @AuthenticationPrincipal UserDetails userDetails) {
                                                                                                                                                                                         
        log.info(userDetails);
        return new ResponseEntity<>(animeService.listAll(pageable), HttpStatus.OK);
    }
    @GetMapping("/list_no_pageable")
    public ResponseEntity<List<Anime>> listAllNoPageable() {
        return new ResponseEntity<>(animeService.listAllNoPageable(),HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Anime> findById(@PathVariable Long id) {
        return new ResponseEntity<>(animeService.findByIdOrThrowBadRequest(id), HttpStatus.OK);
    }
    @GetMapping("/find")
    public ResponseEntity<List<Anime>> findByName(@RequestParam() String name) {
        return new ResponseEntity<>(animeService.findByName(name), HttpStatus.OK);
    }
    @PostMapping("/admin/save")
    public ResponseEntity<Anime> save(@RequestBody @Valid AnimePostRequestBody anime) {

        return new ResponseEntity<>(animeService.save(anime),HttpStatus.CREATED);
    }
    @PostMapping("/test")
    public String post() {
        return "Deu boa";
    }
    
    @DeleteMapping("/admin/{id}")
     public ResponseEntity<Void> delete(@PathVariable Long id){
        animeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping()
    public ResponseEntity<Void> replace(@RequestBody AnimePutRequestBody animePutRequestBody) {
        animeService.replace(animePutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
