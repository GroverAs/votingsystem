package ru.savelyev.votingsystem.to;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import ru.savelyev.votingsystem.HasIdAndEmail;
import ru.savelyev.votingsystem.validation.NoHtml;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserTo extends NamedTo implements HasIdAndEmail {
    @Email
    @NotBlank
    @Size(max = 128)
    @NoHtml  // https://stackoverflow.com/questions/17480809
    String email;

    @NotBlank
    @Size(min = 5, max = 32)
    String password;

    public UserTo(Integer id, String name, String email, String password) {
        super(id, name);
        this.email = email;
        this.password = password;
    }
}
