package com.enigma.tokopakedi.service;

import com.enigma.tokopakedi.constant.Erole;
import com.enigma.tokopakedi.entity.Role;

public interface RoleService {
    Role getOrSave(Erole role);
}
