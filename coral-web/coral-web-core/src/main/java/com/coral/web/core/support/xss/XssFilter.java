/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0; you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.coral.web.core.support.xss;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;

import lombok.extern.slf4j.Slf4j;

/**
 * XSS过滤
 * 
 * @author huss
 */
@Slf4j
public class XssFilter implements Filter {

    private static final List<String> CONTENT_TYPES =
        Arrays.asList(MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE);

    @Override
    public void init(FilterConfig config) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        log.info(">>>>>xss filter start. contentType: {}, request：{}", request.getContentType(), request);

        // json请求需要转换value里面的特殊字符，不在此处处理
        // form-data文件上传暂时也有问题
        if (this.filter(request)) {
            XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest)request);
            chain.doFilter(xssRequest, response);
        } else {
            chain.doFilter(request, response);
        }

    }

    private boolean filter(ServletRequest request) {
        return request.getContentType() != null
            //
            && !CONTENT_TYPES.contains(request.getContentType())
            //
            && !request.getContentType().startsWith(MediaType.MULTIPART_FORM_DATA_VALUE);
    }

    @Override
    public void destroy() {}

}
