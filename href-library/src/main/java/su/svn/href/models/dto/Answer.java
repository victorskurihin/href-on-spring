package su.svn.href.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Answer
{
    private final String status;

    public Answer()
    {
        status = "";
    }
}
