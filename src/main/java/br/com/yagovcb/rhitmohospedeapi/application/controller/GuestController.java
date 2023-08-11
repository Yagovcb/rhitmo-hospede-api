package br.com.yagovcb.rhitmohospedeapi.application.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/guest")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class GuestController {

}
