package rs.ac.bg.fon.njt.groupTraning.service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.njt.groupTraning.model.Member;
import rs.ac.bg.fon.njt.groupTraning.repository.MemberRepository;

@Service
public class MemberService {
    
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository repository) {
        this.memberRepository = repository;
    }

    public Member loadMemberById(Long id) throws Exception {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (!optionalMember.isPresent()) throw new Exception("Member with id = " + id + "does not exist");
        return optionalMember.get();
    }
     public Member loadMemberByJmbg(String jmbg) throws Exception {
        Optional<Member> optionalMember = memberRepository.findByJmbg(jmbg);
        if (!optionalMember.isPresent()) throw new Exception("Member with jmbg = " + jmbg + "does not exist");
        return optionalMember.get();
    }

    public List<Member> loadMembers() {
        return memberRepository.findAll();
    }
    
     public List<Member> loadMembersByGroupId(Long groupId) throws Exception {
       List<Member> members = memberRepository.findByGroupId(groupId);
        if (members.isEmpty()) throw new Exception("Members with groupId = " + groupId + "does not exist");
        return members;
    }

  //  @Transactional
    public Member addMember(Member toAdd) {
//        moguce nakon uspesne autentifikacije
System.out.println(toAdd);
        System.out.println(toAdd.getGroupId());
        return memberRepository.save(toAdd);
    }

    @Transactional
    public Member updateMember(Long id, Member toUpdate) throws Exception {
        Optional<Member> optionalMemeber= memberRepository.findById(id);
        if (!optionalMemeber.isPresent())
            throw new Exception("Cannot update member. Member with id = " + id + "does not exist");
        toUpdate.setId(id);
//        ovo smemo da uradimo jer je jwt token vec proveren od strane Spring Security
  //      newPerformer.getUser().setRole(User.Role.ROLE_ADMIN);
        return memberRepository.save(toUpdate);
    }

   @Transactional
    public Member deleteMemeberById(Long id) throws Exception {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (!optionalMember.isPresent())
            throw new Exception("Cannot delete memeber. Memeber with id = " + id + "does not exist");
        memberRepository.deleteById(id);
        return optionalMember.get();
    }

}
