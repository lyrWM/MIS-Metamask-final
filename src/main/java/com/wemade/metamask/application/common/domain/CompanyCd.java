package com.wemade.metamask.application.common.domain;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * User: Sewon Yoo (ysw@wemade.com)
 * Date: 2022. 06. 30.
 * Time: 오후 7:14
 */
public enum CompanyCd {
    WM("1000","주식회사위메이드"),
    WX("1202","위메이드엑스알"),
    MX("1203","위메이드맥스"),
    WCN("1204","위메이드커넥트"),
    JS("1205","조이스튜디오"),
    LT("1206","라이트컨"),
    WN("1207","위메이드넥스트"),
    WP("1208","위메이드플러스"),
    WMM("1209","위메이드엠"),
    WT("1210","위메이드트리"),
    CQ("1211","전기아이피"),
    AG("132685","합의"),
    WIF("176659","위메이드이프"),
    ET("176660","엔티게임즈"),
    LI("176661","라이크잇게임즈"),
    NX("176662","니트로엑스"),
    FN("176663","플레로엔"),
    NEX("249960","넥셀론"),
    WMX("176664","WEMIX PTE"),
    WMK("254530","WEMIX PTE 한국지점"),
    WIN("176665","위메이드이노베이션"),
    CN("176666","위메이드 중국법인"),
    YC("176667","위메이드 인촨법인"),
    WBJ("176670","위메이드 북경법인"),
    WFJ("240027","위메이드 복건법인"),
    HK("176671","위메이드 홍콩법인"),
    US("176672","위메이드 미국법인"),
    CS("176673","재신전기"),
    SW("176674","상해 위라이즈"),
    KRT("176676","크립톤컴퍼니"),
    WMP("184007","(주)위메이드플레이"),
    PLS("184008","(주)플레이링스"),
    PM("225811","플레이매치컬"),
    PTZ("225812","플레이토즈"),
    TMW("263227","디스민즈워"),
    LS("374920","라이트스케일"),
    ALL("192618","전사"),
    TEST("195483","테스트");

    @Getter
    private final String companyCd;

    @Getter
    private final String companyName;

    CompanyCd(String companyCd, String companyName) {
        this.companyCd = companyCd;
        this.companyName = companyName;
    }

    public static Optional<CompanyCd> find(String companyCd) {
        return Arrays.stream(CompanyCd.values())
                .filter(s -> s.getCompanyCd().equalsIgnoreCase(companyCd))
                .findAny();
    }


    public static List<CompanyCd> getCompanyList(List<CompanyCd> exceptCompanyCdList) {
        return Arrays.stream(CompanyCd.values())
                .filter(s -> !exceptCompanyCdList.contains(s))
                .collect(Collectors.toList());
    }
}
