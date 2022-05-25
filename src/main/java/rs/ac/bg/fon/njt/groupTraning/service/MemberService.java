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
    
    @Autowired
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository repository) {
        this.memberRepository = repository;
    }

    public Member loadMemberById(Long id) throws Exception {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isEmpty()) throw new Exception("Member with id = " + id + "does not exist");
        return optionalMember.get();
    }

    public List<Member> loadMembers() {
        return memberRepository.findAll();
    }

  //  @Transactional
    public Member addMember(Member toAdd) {
//        moguce nakon uspesne autentifikacije
        return memberRepository.save(toAdd);
    }

    @Transactional
    public Member updateMember(Long id, Member toUpdate) throws Exception {
        Optional<Member> optionalMemeber= memberRepository.findById(id);
        if (optionalMemeber.isEmpty())
            throw new Exception("Cannot update member. Member with id = " + id + "does not exist");
        toUpdate.setId(id);
//        ovo smemo da uradimo jer je jwt token vec proveren od strane Spring Security
  //      newPerformer.getUser().setRole(User.Role.ROLE_ADMIN);
        return memberRepository.save(toUpdate);
    }

   @Transactional
    public Member deleteMemeberById(Long id) throws Exception {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isEmpty())
            throw new Exception("Cannot delete memeber. Memeber with id = " + id + "does not exist");
        memberRepository.deleteById(id);
        return optionalMember.get();
    }

}
