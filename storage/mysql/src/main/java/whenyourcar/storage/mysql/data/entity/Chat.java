package whenyourcar.storage.mysql.data.entity;

import jakarta.persistence.*;
import lombok.*;
import whenyourcar.storage.mysql.data.common.DateBaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Chat extends DateBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="chatId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "roomId") // user1을 참조하는 외래키 컬럼
    private Room room;

    @ManyToOne
    @JoinColumn(name = "senderId") // user1을 참조하는 외래키 컬럼
    private User user;

    @Column(nullable = false)
    private String message;
}
