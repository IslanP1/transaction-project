package com.adsifpb.charge_proxy.dto.asaas;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * DTO para lista de clientes retornada pelo ASAAS
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AsaasClienteListResponse {

    @JsonProperty("data")
    private List<AsaasClienteResponse> data;

    @JsonProperty("hasMore")
    private Boolean hasMore;

    @JsonProperty("totalCount")
    private Integer totalCount;

    public List<AsaasClienteResponse> getData() {
        return data;
    }

    public void setData(List<AsaasClienteResponse> data) {
        this.data = data;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
