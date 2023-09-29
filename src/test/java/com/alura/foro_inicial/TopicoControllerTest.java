package com.alura.foro_inicial;

import com.alura.foro_inicial.modelo.curso.Categoria;
import com.alura.foro_inicial.modelo.topico.AgendaDeTopicoService;
import com.alura.foro_inicial.modelo.topico.DatosAgregarTopico;
import com.alura.foro_inicial.modelo.topico.DatosDetalleTopico;
import com.alura.foro_inicial.modelo.topico.StatusTopico;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class TopicoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DatosAgregarTopico> agregarTopicoJacksonTester;

    @Autowired
    private JacksonTester<DatosDetalleTopico> detalleTopicoJacksonTester;

    @MockBean
    private AgendaDeTopicoService agendaDeTopicoService;

    @Test
    @DisplayName("deberia retornar estado http 400 cuando los datos ingresados sean invalidos")
    @WithMockUser
    void agregarTopicoEscenario1() throws Exception {
        //given //when
        var response = mvc.perform(post("/topicos")).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("deberia retornar estado http 200 cuando los datos ingresados son validos")
    @WithMockUser
    void agregarTopicoEscenario2() throws Exception {
        //given
        var fecha = LocalDateTime.now().plusHours(1);
        var titulo = "Duda Java";
        var mensaje = "Tengo una duda en java";
        var categoria = Categoria.JAVA;
        var datos = new DatosDetalleTopico(null, titulo, mensaje, fecha, StatusTopico.NO_RESPONDIDO, 2l, 5l);

        // when

        when(agendaDeTopicoService.agregar(any())).thenReturn(datos);

        var response = mvc.perform(post("/topicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(agregarTopicoJacksonTester.write(new DatosAgregarTopico(titulo, mensaje, 2L, 5L, categoria)).getJson())
        ).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = detalleTopicoJacksonTester.write(datos).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @DisplayName("deberia retornar estado http 404 cuando los datos ingresados sean invalidos")
    @WithMockUser
    void agregarTopicoEscenario3() throws Exception {
        //given //when
        var response = mvc.perform(post("/consultas")).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}