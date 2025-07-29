package cn.iocoder.yudao.module.validation.convert;

import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.validation.controller.admin.reportdefinition.vo.ReportDefinitionRespVO;
import cn.iocoder.yudao.module.validation.dal.dataobject.report.ReportDO;
import cn.iocoder.yudao.module.validation.dal.dataobject.reportdefinition.ReportDefinitionDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReportConvert {

    ReportConvert INSTANCE = Mappers.getMapper(ReportConvert.class);

    default List<ReportDefinitionRespVO> convertList(List<ReportDefinitionDO> list, Map<Long, ReportDO> reportMap) {
        return CollectionUtils.convertList(list, def -> convert(def, reportMap.get(def.getReportId())));
    }

    default ReportDefinitionRespVO convert(ReportDefinitionDO def, ReportDO report) {
        ReportDefinitionRespVO resp = BeanUtils.toBean(def, ReportDefinitionRespVO.class);
        if (report != null) {
            resp.setReportName(report.getName());

        }
        return resp;
    }

}
