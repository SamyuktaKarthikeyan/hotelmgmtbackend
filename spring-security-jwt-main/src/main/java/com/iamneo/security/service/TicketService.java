package com.iamneo.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iamneo.security.entity.Ticket;
import com.iamneo.security.repository.TicketRepository;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public List<Ticket> getTicketsByEmail(String email) {
        return ticketRepository.findByEmail(email);
    }

    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    public void cancelTicket(long id) {
        ticketRepository.deleteById(id);
    }

    public int getTotalCostByEmail(String email) {
        List<Ticket> tickets = getTicketsByEmail(email);
        int totalCost = 0;
        for (Ticket ticket : tickets) {
            totalCost += ticket.getBillCost();
        }
        return totalCost;
    }

}
