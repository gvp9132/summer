package org.gvp.manager.repository.impl;

import lombok.RequiredArgsConstructor;

import org.gvp.common.constant.BaseService;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.gvp.manager.repository.SecurityMenuRepository;
import org.gvp.manager.mapper.SecurityMenuMapper;
import org.gvp.manager.pojo.SecurityMenu;
import java.util.List;
/**
 * 菜单信息 数据层接口实现类
 */
@Repository
@RequiredArgsConstructor
public class SecurityMenuRepositoryImpl implements SecurityMenuRepository {
    private final SecurityMenuMapper securityMenuMapper;

    @Override
    public List<SecurityMenu> searchByParentId(Integer parentId){
        return this.securityMenuMapper.selectList(Wrappers.<SecurityMenu>lambdaQuery()
                .select(
                        SecurityMenu::getId,
                        SecurityMenu::getKey,
                        SecurityMenu::getParentId,
                        SecurityMenu::getPath,
                        SecurityMenu::getIcon,
                        SecurityMenu::getLabel,
                        SecurityMenu::getDescribe,
                        SecurityMenu::getDisabled,
                        SecurityMenu::getShow
                )
                .eq(SecurityMenu::getParentId, parentId));
    }

    @Override
    public int append(SecurityMenu pojo) {
        return this.securityMenuMapper.insert(pojo);
    }

    @Override
    public int modify(SecurityMenu pojo) {
        return this.securityMenuMapper.updateById(pojo);
    }

    @Override
    public int cancel(Integer id) {
        return this.securityMenuMapper.deleteById(id);
    }

    @Override
    public SecurityMenu searchById(Integer id) {
        return this.securityMenuMapper.selectById(id);
    }

    @Override
    public List<SecurityMenu> searchList() {
        return this.securityMenuMapper.selectList(null);
    }

    @Override
    public Long searchCount(String field) {
        return this.securityMenuMapper.selectCount(null);
    }

    @Override
    public List<SecurityMenu> searchLimit(int start, int end) {
        return this.securityMenuMapper.selectList(Wrappers.<SecurityMenu>lambdaQuery()
                .last("LIMIT %d,%d".formatted(start,end))
                .orderByDesc(SecurityMenu::getMenuOrder)
                .orderByDesc(SecurityMenu::getOrder)
                .orderByAsc(SecurityMenu::getId)
        );
    }

    @Override
    public List<SecurityMenu> searchNotLase() {
        return this.securityMenuMapper.selectList(Wrappers.<SecurityMenu>lambdaQuery()
                .select(SecurityMenu::getId, SecurityMenu::getLabel)
                .eq(SecurityMenu::getLast, false)
                .orderByDesc(SecurityMenu::getMenuOrder)
                .orderByDesc(SecurityMenu::getOrder)
        );
    }

    @Override
    public List<SecurityMenu> searchFirstLevel() {
        return this.securityMenuMapper.selectList(Wrappers.<SecurityMenu>lambdaQuery()
                .select(SecurityMenu::getKey,SecurityMenu::getLabel)
                .eq(SecurityMenu::getParentId, BaseService.PARENT_ID)
        );
    }
}
