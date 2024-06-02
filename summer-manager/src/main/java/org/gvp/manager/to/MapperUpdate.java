package org.gvp.manager.to;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 映射更新文件
 */
@NoArgsConstructor
@AllArgsConstructor
public class MapperUpdate {
    /**
     * 更新的数据id
     */
    private Integer id;
    /**
     * 更新的附加数据key
     */
    private List<String> keys;
}
