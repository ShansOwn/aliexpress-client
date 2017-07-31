package com.shansown.aliexpress.api.request;

import com.shansown.aliexpress.api.response.AliResponse;
import com.shansown.aliexpress.api.response.GetPromotionLinksResult;
import com.shansown.aliexpress.config.properties.AliAccessProperty;
import java.util.Objects;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.core.ParameterizedTypeReference;

import static com.shansown.aliexpress.api.AliApi.FIELDS_KEY;
import static com.shansown.aliexpress.api.AliApi.TRACKING_ID_KEY;
import static com.shansown.aliexpress.api.AliApi.URLS_KEY;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GetPromotionLinksRequest extends BaseAliRequest<GetPromotionLinksResult> {

  private static final String API_METHOD = "getPromotionLinks";
  private static final ParameterizedTypeReference<AliResponse<GetPromotionLinksResult>>
      RESULT_TYPE = new ParameterizedTypeReference<AliResponse<GetPromotionLinksResult>>() {
  };

  @Builder
  private GetPromotionLinksRequest(AliAccessProperty access, String fields, String urls) {
    super(access, API_METHOD);
    Objects.requireNonNull(fields, "Fields property required");
    putParamIfPresent(FIELDS_KEY, fields);
    putParamIfPresent(TRACKING_ID_KEY, access.getTrackingId());
    putParamIfPresent(URLS_KEY, urls);
  }

  @Override
  public ParameterizedTypeReference<AliResponse<GetPromotionLinksResult>> getResultType() {
    return RESULT_TYPE;
  }
}
