package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.MapCoordinates;

import java.awt.Point;

/**
 * This enum defines the origin of the labels displaying pawns on the map. They
 * are calculated in a 900x1292 image. They will need to be converted in new
 * values depending on the dimension of the screen.
 * 
 * Every road has a point that serves as the origin for the pawns panel.
 * 
 * The road is rapresented using its ID number (the key in the hashMap of roads
 * in the Game Model)
 * 
 * @author Andrea
 * 
 */
public enum PawnPositions {

	R1(1, new Point(207, 310)), R2(2, new Point(449, 197)), R3(3, new Point(
			609, 208)), R4(4, new Point(132, 454)), R5(5, new Point(263, 397)), R6(
			6, new Point(348, 363)), R7(7, new Point(440, 320)), R8(8,
			new Point(514, 298)), R9(9, new Point(573, 344)), R10(10,
			new Point(629, 389)), R11(11, new Point(731, 303)), R12(12,
			new Point(698, 421)), R13(13, new Point(788, 453)), R14(14,
			new Point(686, 518)), R15(15, new Point(545, 435)), R16(16,
			new Point(418, 415)), R17(17, new Point(263, 507)), R18(18,
			new Point(195, 688)), R19(19, new Point(261, 628)), R20(20,
			new Point(320, 582)), R21(21, new Point(382, 526)), R22(22,
			new Point(455, 479)), R23(23, new Point(520, 524)), R24(24,
			new Point(586, 581)), R25(25, new Point(686, 613)), R26(26,
			new Point(767, 691)), R27(27, new Point(654, 739)), R28(28,
			new Point(528, 617)), R29(29, new Point(464, 665)), R30(30,
			new Point(389, 620)), R31(31, new Point(270, 752)), R32(32,
			new Point(404, 749)), R33(33, new Point(530, 727)), R34(34,
			new Point(581, 816)), R35(35, new Point(664, 899)), R36(36,
			new Point(529, 864)), R37(37, new Point(460, 913)), R38(38,
			new Point(394, 878)), R39(39, new Point(327, 837)), R40(40,
			new Point(207, 948)), R41(41, new Point(328, 1051)), R42(42,
			new Point(526, 1004));

	private Integer roadNumber;
	private Point point;

	private PawnPositions(Integer roadNumber, Point point) {
		this.point = point;
		this.roadNumber = roadNumber;
	}

	public Integer getRoadNumber() {
		return roadNumber;
	}

	public Point getPoint() {
		return point;
	}
}
