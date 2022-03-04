package com.example.demo;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.example.demo.entity.CibilScore;
import com.example.demo.services.CibilScoreService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.val;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; 
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.*;
@SpringBootTest
@AutoConfigureMockMvc
public class TestCibilScoreController {

	
	    @Autowired
	    MockMvc mockMvc;

	    @Autowired
	    ObjectMapper mapper;
	    
	    @MockBean
	    CibilScoreService service;
	   
	    List<CibilScore> records;
	    
	    @BeforeEach
	    void setUp() {
	    
	    	CibilScore RECORD1= new CibilScore("abc123", "ramesh", 600117, 450);
		    
		    CibilScore RECORD2= new CibilScore("pqr456", "vicky", 600044, 750);
		    
		    CibilScore RECORD3= new CibilScore("xyz789", "suresh", 600055, 650);
		    
		    records = new ArrayList<>(Arrays.asList(RECORD1, RECORD2, RECORD3));
	        
	    }
	    
	    @Test
	    void getAllRecords() throws Exception { 
	    	
	   // creating the stub 
	     BDDMockito.given(service.findAll()).willReturn(records);

	    mockMvc.perform(get("/api/v1/scores")
	                   .contentType(MediaType.APPLICATION_JSON))
	                    .andExpect(status().isOk())
	                    .andExpect(jsonPath("$", Matchers.hasSize(3)))
	                    .andExpect(jsonPath("$[2].cardHolderName", Matchers.is("suresh")));
	        }

	    
	    @Test
	    void testGetById() throws Exception { 
	    	
	    	CibilScore obj =  new CibilScore("abc123", "ramesh", 600117, 450);

	     BDDMockito.given(service.findById("abc123")).willReturn(obj);

	    mockMvc.perform(get("/api/v1/scores/abc123")
	                   .contentType(MediaType.APPLICATION_JSON))
	                    .andExpect(status().isOk())
	                    .andExpect(jsonPath("$", Matchers.notNullValue()))
	                    .andExpect(jsonPath("$.cardHolderName",Matchers.is("ramesh")));

	        }

	    
	    @Test
	    void testSave() throws Exception{
	    	
	    	CibilScore toAdd= new CibilScore("123Abc234", "rakki", 600127, 650);

		     BDDMockito.given(service.save(toAdd)).willReturn(toAdd);


	    	       MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v1/scores")
	    	                 .contentType(MediaType.APPLICATION_JSON)
	    	                 .accept(MediaType.APPLICATION_JSON)
	    	                 .content(this.mapper.writeValueAsString(toAdd));
	    	 
	    	         mockMvc.perform(mockRequest)
	    	                 .andExpect(status().isCreated())
	    	                 .andExpect(jsonPath("$", notNullValue()))
	    	                 .andExpect(jsonPath("$.cardHolderName", is("rakki")));
	    }
	    
	    
	    @Test
	    @DisplayName(value = "To Update the Cibil Score for the given Element")
	    void testUpdate() throws Exception {
	    	
	    	
	    	    	CibilScore updatedRecord= new CibilScore("123abc", "giri",600117, 765);

	        
		     BDDMockito.given(service.update(updatedRecord)).willReturn(updatedRecord);
	        
	        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/v1/scores")
	                .contentType(MediaType.APPLICATION_JSON)
	                .accept(MediaType.APPLICATION_JSON)
	                .content(this.mapper.writeValueAsString(updatedRecord));
	        
	        mockMvc.perform(mockRequest)
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$", notNullValue()))
	                .andExpect(jsonPath("$.score", is(765.0)));
	    }

	    
	    @Test
	    void testDelete() throws Exception {
	    	


	    
	                mockMvc.perform(MockMvcRequestBuilders
	                        .delete("/api/v1/scores/abc123")
	                        .contentType(MediaType.APPLICATION_JSON))
	                        .andExpect(status().isNoContent());
	        
	    }

}
	    
	   

