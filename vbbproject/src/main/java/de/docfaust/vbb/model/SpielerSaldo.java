package de.docfaust.vbb.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * SpielerSaldo Model.
 * @author wfa339
 *
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor(staticName = "of")
public class SpielerSaldo {
	@NonNull private String spielerName;
	@NonNull private BigDecimal saldo = BigDecimal.ZERO;
	private int activityLevel = 0;
}
