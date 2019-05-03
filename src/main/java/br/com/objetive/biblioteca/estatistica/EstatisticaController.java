package br.com.objetive.biblioteca.estatistica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

/**
 * @author Augusto Scher
 */
@RestController
@RequestMapping("v1/estatistica")
public class EstatisticaController {

    @Autowired
    private EstatisticaService service;

    @GetMapping
    @ApiOperation(value = "Retorna estátisticas", response = Estatistica.class)
    public ResponseEntity<?> get(Authentication auth) {
        return new ResponseEntity<>(service.getEstatistica(), HttpStatus.OK);
    }

}
