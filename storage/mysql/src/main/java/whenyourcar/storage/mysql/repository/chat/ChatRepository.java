package whenyourcar.storage.mysql.repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import whenyourcar.storage.mysql.data.entity.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
