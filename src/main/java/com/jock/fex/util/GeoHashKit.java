package com.jock.fex.util;

import java.util.HashMap;
import java.util.Map;

public class GeoHashKit {
	private static char[] _base32 = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	private final static Map _decodemap = new HashMap();
	static {
		int sz = _base32.length;
		for (int i = 0; i < sz; i++) {
			_decodemap.put(_base32[i], i);
		}
	}
	private static int precision = 12;
	private static int[] bits = { 16, 8, 4, 2, 1 };

	public static void main(String[] args) {
		/*
		 * GeoHashKit ghf = new GeoHashKit();
		 * 
		 * 
		 * String gc1 = ghf.encode(99.260085,23.140941); String gc2 =
		 * ghf.encode(23.280890,23.122563);
		 * 
		 * System.out.println(gc1); System.out.println(gc2);
		 * 
		 * double[] gd1 = ghf.decode(gc1); double[] gd2 = ghf.decode(gc2);
		 * System.out.println(gd1[0] + ", " + gd1[1]); System.out.println(gd2[0] + ", "
		 * + gd2[1]);
		 * 
		 * System.out.println(GeoHashKit.getDistatce(gd1[0],gd1[1],gd2[0],gd2[1]));
		 */

		// System.out.println(ghf.encode(30.657356, 104.057447));
		// System.out.println(ghf.encode(30.661371, 104.053026));

		GeoHash geoHash = GeoHash.encode(23.149712, 113.230333);
		System.out.println(geoHash.lat + "," + geoHash.lon + "          " + geoHash.toHashString());
		GeoHash decodeHash1 = GeoHash.decode(geoHash.hash);
		System.out.println(decodeHash1.lat + "," + decodeHash1.lon + "          " + decodeHash1.toHashString());

		GeoHash geoHash2 = GeoHash.encode(23.122662, 113.332866);
		System.out.println(geoHash2.lat + "," + geoHash2.lon + "          " + geoHash2.toHashString());
		System.out
				.println(GeoHash.decode(geoHash2.toHashString()).lat + "," + GeoHash.decode(geoHash2.toHashString()).lon
						+ "          " + GeoHash.decode(geoHash2.toHashString()).toHashString());
		// System.out.println(GeoHash.encode(113.28089,23.122563));

		System.out.println(GeoHashKit.getDistance(geoHash.lat, geoHash2.lat, geoHash.lon, geoHash2.lon));

	}

	public static String encode(double latitude, double longitude) {
		double[] lat_interval = { -90.0, 90.0 };
		double[] lon_interval = { -180.0, 180.0 };

		StringBuilder geohash = new StringBuilder();
		boolean is_even = true;
		int bit = 0, ch = 0;

		while (geohash.length() < precision) {
			double mid = 0.0;
			if (is_even) {
				mid = (lon_interval[0] + lon_interval[1]) / 2;
				if (longitude > mid) {
					ch |= bits[bit];
					lon_interval[0] = mid;
				} else {
					lon_interval[1] = mid;
				}

			} else {
				mid = (lat_interval[0] + lat_interval[1]) / 2;
				if (latitude > mid) {
					ch |= bits[bit];
					lat_interval[0] = mid;
				} else {
					lat_interval[1] = mid;
				}
			}

			is_even = is_even ? false : true;

			if (bit < 4) {
				bit++;
			} else {
				geohash.append(_base32[ch]);
				bit = 0;
				ch = 0;
			}
		}

		return geohash.toString();
	}

	public static double[] decode(String geohash) {
		double[] ge = decode_exactly(geohash);
		double lat, lon, lat_err, lon_err;
		lat = ge[0];
		lon = ge[1];
		lat_err = ge[2];
		lon_err = ge[3];

		double lat_precision = Math.max(1, Math.round(-Math.log10(lat_err))) - 1;
		double lon_precision = Math.max(1, Math.round(-Math.log10(lon_err))) - 1;

		lat = getPrecision(lat, lat_precision);
		lon = getPrecision(lon, lon_precision);

		return new double[] { lat, lon };
	}

	public static double[] decode_exactly(String geohash) {
		double[] lat_interval = { -90.0, 90.0 };
		double[] lon_interval = { -180.0, 180.0 };

		double lat_err = 90.0;
		double lon_err = 180.0;
		boolean is_even = true;
		int sz = geohash.length();
		int bsz = bits.length;
		double latitude, longitude;
		for (int i = 0; i < sz; i++) {
			int cd = (Integer) _decodemap.get(geohash.charAt(i));
			for (int z = 0; z < bsz; z++) {
				int mask = bits[z];
				if (is_even) {
					lon_err /= 2;
					if ((cd & mask) != 0) {
						lon_interval[0] = (lon_interval[0] + lon_interval[1]) / 2;
					} else {
						lon_interval[1] = (lon_interval[0] + lon_interval[1]) / 2;
					}

				} else {
					lat_err /= 2;

					if ((cd & mask) != 0) {
						lat_interval[0] = (lat_interval[0] + lat_interval[1]) / 2;
					} else {
						lat_interval[1] = (lat_interval[0] + lat_interval[1]) / 2;
					}
				}
				is_even = is_even ? false : true;
			}

		}
		latitude = (lat_interval[0] + lat_interval[1]) / 2;
		longitude = (lon_interval[0] + lon_interval[1]) / 2;

		return new double[] { latitude, longitude, lat_err, lon_err };
	}

	static double getPrecision(double x, double precision) {
		double base = Math.pow(10, -precision);
		double diff = x % base;
		return x - diff;
	}

	private final static double PI = 3.14159265358979323; // 圆周率
	private final static double R = 6371229; // 地球的半径

	public static double getDistance(double lat1, double lat2, double lon1, double lon2) {
		double x, y, distance;
		x = (lon2 - lon1) * PI * R * Math.cos(((lat1 + lat2) / 2) * PI / 180) / 180;
		y = (lat2 - lat1) * PI * R / 180;
		distance = Math.hypot(x, y);
		return distance;
	}

}
