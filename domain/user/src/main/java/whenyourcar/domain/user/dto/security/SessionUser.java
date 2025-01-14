package whenyourcar.domain.user.dto.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@AllArgsConstructor
public class SessionUser implements Serializable {
    @Serial
    private static final long serialVersionUID = 859896355423602976L;

    private Long userId;
    private String name;
    private String email;
    private String picture;
}
