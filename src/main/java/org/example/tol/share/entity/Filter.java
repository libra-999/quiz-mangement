package org.example.tol.share.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
public class Filter{

    private boolean status;
    private List<String> value;
    private Date startDate;
    private Date endDate;
}
