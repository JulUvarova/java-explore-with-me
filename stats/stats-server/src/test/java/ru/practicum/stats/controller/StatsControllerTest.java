package ru.practicum.stats.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.stats.service.StatsService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatsControllerTest {
    @Mock
    private StatsService service;
    @InjectMocks
    private StatsController controller;
    private MockMvc mvc;
    private EndpointHitDto dto;

    @Autowired
    private ObjectMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    @BeforeEach
    void init() {
        mvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        dto = EndpointHitDto.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .ip("192.163.0.1")
                .timestamp(LocalDateTime.of(2022, 9, 6, 11, 0, 23))
                .build();
    }

    @Test
    void addHit_whenValidDto_thenCreated() throws Exception {
        mvc.perform(post("/hit")
                        .content(mapper.writeValueAsString(dto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        verify(service, times(1)).addHit(dto);
    }

    @Test
    void addHit_whenEmptyApp_thenReturnBadRequest() throws Exception {
        dto = EndpointHitDto.builder()
                .app("")
                .build();

        mvc.perform(post("/hit")
                        .content(mapper.writeValueAsString(dto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
        verifyNoInteractions(service);
    }

    @Test
    void addHit_whenInvalidApp_thenReturnBadRequest() throws Exception {
        dto = EndpointHitDto.builder()
                .app("soooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "biiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii" +
                        "gaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaap")
                .build();

        mvc.perform(post("/hit")
                        .content(mapper.writeValueAsString(dto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
        verifyNoInteractions(service);
    }

    @Test
    void addHit_whenEmptyUri_thenReturnBadRequest() throws Exception {
        dto = EndpointHitDto.builder()
                .uri("")
                .build();

        mvc.perform(post("/hit")
                        .content(mapper.writeValueAsString(dto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
        verifyNoInteractions(service);
    }

    @Test
    void addHit_whenInvalidUri_thenReturnBadRequest() throws Exception {
        dto = EndpointHitDto.builder()
                .uri("soooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "biiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii" +
                        "gaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaap")
                .build();

        mvc.perform(post("/hit")
                        .content(mapper.writeValueAsString(dto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
        verifyNoInteractions(service);
    }

    @Test
    void addHit_whenEmptyIp_thenReturnBadRequest() throws Exception {
        dto = EndpointHitDto.builder()
                .ip("")
                .build();

        mvc.perform(post("/hit")
                        .content(mapper.writeValueAsString(dto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
        verifyNoInteractions(service);
    }

    @Test
    void addHit_whenInvalidIp_thenReturnBadRequest() throws Exception {
        dto = EndpointHitDto.builder()
                .app("wrong.ip")
                .build();

        mvc.perform(post("/hit")
                        .content(mapper.writeValueAsString(dto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
        verifyNoInteractions(service);
    }

    @Test
    void addHit_whenEmptyTimestamp_thenReturnBadRequest() throws Exception {
        dto = EndpointHitDto.builder()
                .timestamp(null)
                .build();

        mvc.perform(post("/hit")
                        .content(mapper.writeValueAsString(dto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
        verifyNoInteractions(service);
    }

    @Test
    void getStats_whenValidParam_returnList() throws Exception {
        String start = "2020-05-05 00:00:00";
        String end = "2035-05-05 00:00:00";

        ViewStatsDto dto = ViewStatsDto.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .hits(6L)
                .build();

        when(service.getStats(any(LocalDateTime.class), any(LocalDateTime.class), anyList(), anyBoolean()))
                .thenReturn(List.of(dto));

        mvc.perform(get("/stats")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("start", start)
                        .param("end", end)
                        .param("uris", "null")
                        .param("unique", "false")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].app", is(dto.getApp())))
                .andExpect(jsonPath("$[0].uri", is(dto.getUri())))
                .andExpect(jsonPath("$[0].hits", is(dto.getHits()), Long.class));

        verify(service, times(1)).getStats(any(LocalDateTime.class), any(LocalDateTime.class), anyList(), anyBoolean());
    }

    @Test
    void getStats_whenStartIsNotExist_returnBadRequest() throws Exception {
        String end = "2025-05-05 00:00:00";

        mvc.perform(get("/stats")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("end", end)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
        verifyNoInteractions(service);
    }

    @Test
    void getStats_whenEndIsNotExist_returnBadRequest() throws Exception {
        String start = "2025-05-05 00:00:00";

        mvc.perform(get("/stats")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("start", start)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
        verifyNoInteractions(service);
    }

    @Test
    void getStats_whenInvalidDataForm_returnBadRequest() throws Exception {
        String start = "2035.05.05 00:00:00";
        String end = "2025-05-05 00:00:00";

        mvc.perform(get("/stats")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("start", start)
                        .param("end", end)
                        .param("uris", "null")
                        .param("unique", "false")
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
        verifyNoInteractions(service);
    }
}