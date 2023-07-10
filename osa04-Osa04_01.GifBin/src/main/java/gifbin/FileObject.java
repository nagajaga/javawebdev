package gifbin;

import javax.persistence.Entity;
import javax.persistence.Lob;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class FileObject extends AbstractPersistable<Long>{
    @Lob
    private byte[] content;
    
}
