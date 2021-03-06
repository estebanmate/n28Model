package es.bankia.n28;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class N28Controller {

	// inject via application.properties
	@Value("${inicio.mensaje:test}")
	private String mensaje = "Hola";

	@RequestMapping("/")
	public String inicio(Map<String, Object> model) {
		model.put("mensaje", this.mensaje);
		return "inicio";
	}

}