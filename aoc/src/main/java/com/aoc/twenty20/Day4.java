package com.aoc.twenty20;

import com.aoc.common.Challenge;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * See <a href="https://adventofcode.com/2020/day/4">Day 4 question</a>
 */
public class Day4 extends Challenge {

    protected Day4() {
        super(4, 2020);
    }

    public static void main(String[] args) {
        new Day4().executeTasks();
    }

    @Override
    protected Object task1() {
        return verifyPassports(Passport::hasRequiredFields);
    }

    @Override
    protected Object task2() {
        return verifyPassports(Passport::isValid);
    }

    private Object verifyPassports(Predicate<Passport> verifyFunction) {
        List<Passport> passports = loadPassports();
        return passports.stream().filter(verifyFunction).count();
    }

    private List<Passport> loadPassports() {
        List<Passport> passports = new ArrayList<>();
        if (inputList == null || inputList.isEmpty()) {
            return passports;
        }
        Passport passport;
        String key, val;
        for (String passportPage : getResourceAsString().split("\n\n")) {
            passport = new Passport();
            String[] fields = passportPage.replaceAll("\n", " ").split(" ");
            for (String field : fields) {
                key = field.split(":")[0];
                val = field.split(":")[1];
                switch (key) {
                    case "byr":
                        passport.setByr(val);
                        break;
                    case "iyr":
                        passport.setIyr(val);
                        break;
                    case "eyr":
                        passport.setEyr(val);
                        break;
                    case "hgt":
                        passport.setHgt(val);
                        break;
                    case "hcl":
                        passport.setHcl(val);
                        break;
                    case "ecl":
                        passport.setEcl(val);
                        break;
                    case "pid":
                        passport.setPid(val);
                        break;
                    case "cid":
                        passport.setCid(val);
                        break;
                }
            }
            passports.add(passport);
        }
        return passports;
    }

    class Passport {
        String byr;
        String iyr;
        String eyr;
        String hgt;
        String hcl;
        String ecl;
        String pid;
        String cid;

        public boolean hasRequiredFields() {
            return byr != null && iyr != null && eyr != null && hgt != null && hcl != null
                && ecl != null && pid != null;
        }

        public boolean isValid() {
            return isByrValid() && isIyrValid() && isEyrValid() && isHgtValid() && isHclValid() &&
                isEclValid() && isPidValid();
        }

        private boolean isByrValid() {
            return isYearInTheRange(byr, 1920, 2002);
        }

        private boolean isIyrValid() {
            return isYearInTheRange(iyr, 2010, 2020);
        }

        private boolean isEyrValid() {
            return isYearInTheRange(eyr, 2020, 2030);
        }

        private boolean isYearInTheRange(String year, int oldestYear, int latestYear) {
            return year != null && year.length() == 4 &&
                Integer.parseInt(year) >= oldestYear && Integer.parseInt(year) <= latestYear;
        }

        private boolean isHgtValid() {
            return hgt != null && (
                (hgt.endsWith("cm") && Integer.parseInt(hgt.replaceAll("cm", "")) >= 150 &&
                    Integer.parseInt(hgt.replaceAll("cm", "")) <= 193) ||
                    (hgt.endsWith("in") && Integer.parseInt(hgt.replaceAll("in", "")) >= 59 &&
                        Integer.parseInt(hgt.replaceAll("in", "")) <= 76));
        }

        private boolean isPidValid() {
            boolean hasRightLength = pid != null && pid.length() == 9;
            if (hasRightLength) {
                for (int i = 0; i < 9; i++) {
                    if (!Character.isDigit(pid.charAt(i))) {
                        return false;
                    }
                }
            }
            return hasRightLength;
        }

        private boolean isEclValid() {
            return ecl != null && (ecl.equals("amb") || ecl.equals("blu") || ecl.equals("brn") ||
                ecl.equals("gry") || ecl.equals("grn") || ecl.equals("hzl") || ecl.equals("oth"));
        }

        private boolean isHclValid() {
            boolean hasRightLength = hcl != null && hcl.length() == 7 && hcl.charAt(0) == '#';
            if (hasRightLength) {
                for (int i = 1; i < 7; i++) {
                    if (!isValidDigitOrChar(hcl.charAt(i), 'a', 'f')) {
                        return false;
                    }
                }
            }
            return hasRightLength;
        }

        private boolean isValidDigitOrChar(char ch, char startingChar, char endingChar) {
            return Character.isDigit(ch) || (ch >= startingChar && ch <= endingChar);
        }

        public void setByr(String byr) {
            this.byr = byr;
        }

        public void setIyr(String iyr) {
            this.iyr = iyr;
        }

        public void setEyr(String eyr) {
            this.eyr = eyr;
        }

        public void setHgt(String hgt) {
            this.hgt = hgt;
        }

        public void setHcl(String hcl) {
            this.hcl = hcl;
        }

        public void setEcl(String ecl) {
            this.ecl = ecl;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }
    }
}
