package com.sportsbook.repository.tenant.ticket;

import com.sportsbook.model.tenant.Ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByPlayerIdOrderByPlacedAtDesc(Long playerId);
}
