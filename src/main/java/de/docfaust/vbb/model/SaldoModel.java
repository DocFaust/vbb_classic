package de.docfaust.vbb.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * SaldoModel.
 * @author wfa339
 *
 */
@Getter
@Setter
@ToString
public class SaldoModel {
	private BigDecimal completeSaldo = BigDecimal.ZERO;
	private List<SpielerSaldo> spielersaldi = new ArrayList<SpielerSaldo>();
}
