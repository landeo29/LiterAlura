package com.alura.Liter_Alura;

import com.alura.Liter_Alura.principal.Principal;
import com.alura.Liter_Alura.repository.AutorRepository;
import com.alura.Liter_Alura.repository.LibroRepository;
import com.alura.Liter_Alura.services.EstadisticasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {
	@Autowired
	private LibroRepository repository;

	@Autowired
	private AutorRepository autorRepository;

	@Autowired
	private EstadisticasService estadisticasService;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repository, autorRepository, estadisticasService);
		principal.muestraMenu();
	}
}
