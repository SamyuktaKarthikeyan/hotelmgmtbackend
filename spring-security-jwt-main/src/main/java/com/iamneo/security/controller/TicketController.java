package com.iamneo.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iamneo.security.entity.Ticket;
import com.iamneo.security.service.TicketService;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "http://localhost:3000")
public class TicketController {

    @Autowired
    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/book")
    public ResponseEntity<Ticket> bookTicket(@RequestBody Ticket ticket) {
        Ticket savedTicket = ticketService.saveTicket(ticket);
        return new ResponseEntity<>(savedTicket, HttpStatus.CREATED);
    }

    @GetMapping("/getTickets/{email}")
    public ResponseEntity<List<Ticket>> getTicketsByEmail(@PathVariable String email) {
        List<Ticket> tickets = ticketService.getTicketsByEmail(email);
        if (tickets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(tickets, HttpStatus.OK);
        }
    }

    @DeleteMapping("/cancelTicket/{id}")
    public ResponseEntity<String> cancelTicket(@PathVariable Long id) {
        Ticket ticket = ticketService.getTicketById(id);
        if (ticket == null) {
            return new ResponseEntity<>("Ticket not found", HttpStatus.NOT_FOUND);
        }

        ticketService.cancelTicket(id);
        return new ResponseEntity<>("Ticket canceled successfully", HttpStatus.OK);
    }

    @GetMapping("/totalCost/{email}")
    public ResponseEntity<Integer> getTotalCostByEmail(@PathVariable String email) {
        int totalCost = ticketService.getTotalCostByEmail(email);
        return new ResponseEntity<>(totalCost, HttpStatus.OK);
    }

}
